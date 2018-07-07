/**
 * @author kongdexin
 * @Description
 * @create 2018-06-05 下午 14:41
 **/
public class Features {

    /**
     * 特征值
     */
    private float[] featureValue = new float[512];

    private long createTime;

    private long id;

    private String cameraName;

    public Features() {
    }


    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public Features(float[] featureValue, long createTime) {
        this.featureValue = featureValue;
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float[] getFeatureValue() {
        return featureValue;
    }

    public void setFeatureValue(float[] featureValue) {
        this.featureValue = featureValue;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}