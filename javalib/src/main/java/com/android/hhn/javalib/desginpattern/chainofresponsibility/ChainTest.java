package com.android.hhn.javalib.desginpattern.chainofresponsibility;

/**
 * Author: haonan.he ;<p/>
 * Date: 2/7/23,4:14 PM ;<p/>
 * Description: 责任链模式简单示例;<p/>
 * Other: ;
 */
class ChainTest {
    public static void main(String[] args) {
        Request request = new Request.RequestBuilder().setStep1(true).setStep2(true).build();
        FirstHandler firstHandler = new FirstHandler(new SecondHandler(null));
        if (firstHandler.process(request)) {
            System.out.println("所有责任链处理完毕");
        } else {
            System.out.println("部分步骤错误，责任链中断");
        }
    }
}

class Request {
    private boolean step1;
    private boolean step2;

    private Request(boolean step1, boolean step2) {
        this.step1 = step1;
        this.step2 = step2;
    }

    public boolean isStep1() {
        return step1;
    }

    public boolean isStep2() {
        return step2;
    }

    static class RequestBuilder {
        private boolean step1;
        private boolean step2;
        private boolean step3;

        RequestBuilder setStep1(boolean step1) {
            this.step1 = step1;
            return this;
        }

        RequestBuilder setStep2(boolean step2) {
            this.step2 = step2;
            return this;
        }

        public Request build() {
            Request request = new Request(step1, step2);
            return request;
        }
    }
}

abstract class StepHandler {
    StepHandler next;

    public StepHandler(StepHandler next) {
        this.next = next;
    }

    public StepHandler getNext() {
        return next;
    }

    abstract boolean process(Request request);
}

class FirstHandler extends StepHandler {
    public FirstHandler(StepHandler next) {
        super(next);
    }

    @Override
    boolean process(Request request) {
        if (request.isStep1()) {
            System.out.println("FirstHandler success");
            StepHandler next = getNext();
            if (next == null) {
                System.out.println("责任链结束");
                return true;
            } else {
                System.out.println("触发下一个步骤");
                return next.process(request);
            }
        } else {
            System.out.println("FirstHandler error");
            return false;
        }
    }
}

class SecondHandler extends StepHandler {
    public SecondHandler(StepHandler next) {
        super(next);
    }

    @Override
    boolean process(Request request) {
        if (request.isStep2()) {
            System.out.println("SecondHandler success");
            StepHandler next = getNext();
            if (next == null) {
                System.out.println("责任链结束");
                return true;
            } else {
                System.out.println("触发下一个步骤");
                return next.process(request);
            }
        } else {
            System.out.println("SecondHandler error");
            return false;
        }
    }
}