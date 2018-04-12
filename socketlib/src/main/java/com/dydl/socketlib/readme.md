### OkSocket3.2 --Bite 测试版 ###
    1.利用反射处理 泛型T ，获取T对应的class
    2.优化success成功处理方法

### OkSocket3.1错误集合更新 ### 

    1.error继承了Exception类
    2.调整listern监听接口里的 监听数量
       2.1 删除onSendFild()和onReviceFild()接口
       2.2 将onRevice()接口改为onSuccess();
       2.3 添加onError()接口，参数返回ApiException e,封装具体错误
       2.4 对应封装onSuccess()方法，将传入数据用泛型调用<T>
       
       
### OkSocket3.0更新上传参数 ###

- 更新上传格式 --将原有的 封装成bean类 转成toString的方法 变成 用Map的集合,方便填写
~~~
    1.添加Helper类，OkStrHelper
    2.添加Params类
    3.修改DialogUtils类的Style
~~~
### OkSocket2.2修改 ###
    1.调整base类,上传数据的时候不需要添加创建bean
    2.修改|1212|2112|ddd|saad&hhh&;sdhsd&sdd&;|ddd|false|
      2.1对应格式，建立StringButter
    3.修改error集合，将请求成功获取失败，请求为空（无任何值）的情景修改

### OkSocket2.1修改 ###
    1.对失败的数据进行了集合整理
    2.error集合（替换onNext()方法改为onError()）
    3.添加延时装置，减少socket传世冲突(delaySubscription(1, TimeUnit.SECONDS)  //延迟1s订阅)
    4.订阅器调用

### OkSocket2.0使用 ###
    1.在BaseApplication中添加 OkSocket.getInstance(mContext);
    2.在连接socket网络的位置 添加 OkSocket.sendMsg(OkBase,new Callback());

### OkSocket2.0流程 ###
    1.采用socket传输，将OkBase数据 String的形式用StringButter连接
    2.获取的数据 进行内部处理 --用的方法是rxjava+rxandroid的形式封装。
    3.Observable - subscribe  rxjava的设计思想。具体封装请看代码。

### OkSocket2.0优化 ###
    1.用懒汉式的 单例模式，在BaseApplication一次封装，可多次调用。
    2.为了减少内存溢出的情况，使用Context的时候调用context.getApplicationContext();方法
    3.使用rxJava的订阅器，实现一次请求一次回收。
    4.代码内部封装，无需展示在外部，工厂设计模式统一调用。
    5.用callback封装listen接口，无需将listen接口数据全部重写。
    6.在callback里封装dialog弹窗，解决请求中--请求结束之间的等待问题。

### OkSocket2.0后期优化 ###
    1.创建error错误Listener接口。对应错误进行抛出。
    2.post端口未处理在OkSocket框架中。

### OkSocket2.0与之前封装Thread+Handler的OkSocket1.0区别 ###
        1.OkSocket2.0使用的是RxJava+RxAndroid的代码形式编写，链式调用方便理解
        2.用异步的形式处理数据之间的逻辑，没有开启线程处理用Handler返回，减少了代码量。
        3.开启线程的时候需要new(创建)一个线程，导致每个网络请求都要开启3个线程执行操作。
        4.观察发现，线程中的操作，主要是为了筛选我们想要的数据。用handler抛出后，又继续筛选数据，直到返回我们想要的数据。
          所以，可以用rxjava异步处理，筛选出我们想要的数据。防止不停(创建)new线程。减少内存溢出。

### 方便理解，将之前封装的Thread+Handler的OkSocket1.0代码贴出来。 ###
    public static void sendMsg(final BaseBean bean, final int port,
                                   final OkCallBack listener) {
            XLog.json(new Gson().toJson(bean));
            listener.onStart();
            StringBuilder builder = new StringBuilder();
            builder.append(bean.toString());
            int strLength = StringUtil.getLengthContainsCn(builder.toString(), "UTF-8");
            String length = StringUtil.complementBit(Integer.toString(strLength),
                    "0", 5, 1);
            builder.insert(0, length);
            sendStr = builder.toString();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    String url = null;
                    try {
                        Socket socket = new Socket();
                        if (MySharedPreference.isContains(Constants.SERVER_IP)) {
                            url = MySharedPreference.getString(Constants.SERVER_IP);
                        }
                        socket.connect(new InetSocketAddress(url, port), 10000);
                        PrintWriter writer = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream(), "UTF-8")));
                        writer.print(sendStr);
                        writer.flush();
                        SocketBean bean = new SocketBean()
                                .setSocket(socket)
                                .setValue(sendStr)
                                .setListener(listener);
                        Message message = new Message();
                        message.what = Constants.SUCCESS_SEND;// 成功
                        message.obj = bean;
                        mHandler.sendMessage(message);
                    } catch (Exception e) {
                        Message message = new Message();
                        message.what = Constants.FAILED_SEND;// 失败
                        message.obj = listener;
                        mHandler.sendMessage(message);
                    }
                    Looper.loop();
                }
            }).start();
        }
    
        public static void getResultData() {//获取数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    try {
                        Socket socket = SocketBean.mCurrentSocket;
                        InputStream in = socket.getInputStream();
                        DataInputStream dis = new DataInputStream(in);
                        socket.setSoTimeout(10000);
                        byte[] valueByte = new byte[15360];
                        int size;
                        while (true) {
                            try {
                                if ((size = dis.read(valueByte)) > 0 || size == -1) {
                                    String hexValue = StringUtil.bytesToHexStr(valueByte, size == -1 ? 0 : size);
                                    String value = StringUtil.strHexToStr(hexValue, "UTF-8");
                                    //TODO****数据长度过长可能出现接收到的数据不全，没有报文结尾符"|"后期需注意规避
                                    SocketBean bean = new SocketBean()
                                            .setSocket(socket)
                                            .setValue(value);
                                    Message message = new Message();
                                    message.what = Constants.SUCCESS;
                                    message.obj = bean;
                                    mHandler.sendMessage(message);
                                    dis.close();
                                    in.close();
                                    return;
                                }
                            } catch (SocketTimeoutException e) {
                                Message message = new Message();
                                SocketBean bean = new SocketBean()
                                        .setValue("等待响应超时!");
                                message.what = Constants.FAILED;
                                message.obj = bean;
                                mHandler.sendMessage(message);
                                return;
                            }
                        }
    
                    } catch (Exception e) {
                        Message message = new Message();
                        SocketBean bean = new SocketBean()
                                .setValue(e.getMessage());
                        message.what = Constants.FAILED;
                        message.obj = bean;
                        mHandler.sendMessage(message);
                    }
                    Looper.loop();
                }
            }).start();
        }
    
        private static class FHandler extends Handler {//防止内存泄露   用静态内部类 + 弱引用的方式
    
            private WeakReference<Context> reference;
    
            public FHandler(Context context) {
                reference = new WeakReference<Context>(context);
            }
    
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Constants.SUCCESS_SEND://上传成功
                        SocketBean bean = (SocketBean) msg.obj;
                        SocketBean.mCurrentSocket = bean.getSocket();
                        OkCallBack listener1 = bean.getListener();
                        setOnReceiverListener(listener1);
                        getResultData();
                        break;
                    case Constants.FAILED_SEND://上传失败
                        ((OkCallBack) msg.obj).onSendFailed();
                        break;
                    case Constants.SUCCESS://返回数据成功
                        SocketBean bean1 = (SocketBean) msg.obj;
                        SocketBean.mCurrentSocket = bean1.getSocket();
                        try {
                            if (SocketBean.mCurrentSocket != null) {
                                SocketBean.mCurrentSocket.close();
                                SocketBean.mCurrentSocket = null;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mListener.onReceiver(bean1.getValue());
                        break;
                    case Constants.FAILED://返回数据失败
                        mListener.onReceiveFailed(((SocketBean) msg.obj).getValue());
                        break;
                    default:
                        break;
                }
            }
        }
### OkSocket1.0的优化 ###
    1.将Handler以内部类的形式创建，通过弱引用创建Handler,解决handler多次调用。
    2.将上传成功上传失败，获取数据成功获取数据失败的方法全部封装到一个handler里面
    3.弱引用+软引用的模式，将无用的handler gc掉。（但是线程创建的比较多，一个网络请求创建3个线程）