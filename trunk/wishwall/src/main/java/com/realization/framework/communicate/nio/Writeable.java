package com.realization.framework.communicate.nio;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 网络写入.
 * 
 * 		@ans 空的为什么还要写入？？？
 * 
 * @author <a href="mailto:zhengduan@angelshine.net">zhengduan</a>
 *
 */
public interface Writeable {
    
	//TODO 一个空的写入回复
    Future<?> EMPTY_FUTURE = new Future<Object>() {
        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }
        @Override
        public boolean isCancelled() {
            return false;
        }
        @Override
        public boolean isDone() {
            return true;
        }
        @Override
        public Object get() throws InterruptedException, ExecutionException {
            return null;
        }
        @Override
        public Object get(long timeout, TimeUnit unit) throws InterruptedException,ExecutionException,TimeoutException {
            return null;
        }
    };

    /**
     * 判断链路是否通畅.
     * 
     */
    boolean isConnected();

    /**
     * 异步写入.
     * 
     * @param message 写入消息.
     * 
     * @return 返回异步结果，如果没有响应，则返回null.
     * 
     */
    Future<?> write(Object message);
}
