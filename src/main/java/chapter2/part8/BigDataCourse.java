package chapter2.part8;

public class BigDataCourse extends NetworkCourse {

    private boolean needHomeworkFlag = false; 
    
    
    
    public BigDataCourse(boolean needHomeworkFlag) {
        super();
        this.needHomeworkFlag = needHomeworkFlag;
    }

    @Override
    protected void checkHomework() {
        // TODO Auto-generated method stub
        System.out.println("检查大数据的课后作业");
    }

    @Override
    protected boolean needHomework() {
        // TODO Auto-generated method stub
        return this.needHomeworkFlag;
    }
}
