package chapter2.part8;
// 模板会有一个或多个为实现的方法,而且这几个为实现的方法有固定的执行顺序
public abstract class NetworkCourse {

    protected final void createCourse() {
        // 发布预习资料
        this.postPreResource();
    
        // 制作课件PPT
        this.createPPT();
        
        // 在线直播
        this.liveVideo();
    
        // 提交课堂笔记
        this.postNote();
    
        // 提交代码
        this.postSource();
    
        // 布置作业,有些课是有作业的,有些课是没有作业的
        // 如果有作业就检查作业，如果没有泽流程结束
        if(needHomework()) {
            checkHomework();
        }
    }
    
    protected abstract void checkHomework();

    // 钩子方法实现流程的微调
    protected boolean needHomework() {
        return false;
    }

    private void postSource() {
        // TODO Auto-generated method stub
        System.out.println("提交源代码");
    }

    private void postNote() {
        // TODO Auto-generated method stub
        System.out.println("提交课件和笔记");
    }

    private void liveVideo() {
        // TODO Auto-generated method stub
        System.out.println("直播授课");
    }

    private void createPPT() {
        // TODO Auto-generated method stub
        System.out.println("创建备课PPT");
    }

    private void postPreResource() {
        // TODO Auto-generated method stub
        System.out.println("分发预习材料");
    }
}
