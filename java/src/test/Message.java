/**
 * @author jianghong
 * @Description
 * @create 2018-06-12 16:59
 **/
public class Message {
    private String imageId;
    private String name;
    private int errCode;
    private String errReason;
    public String getImageId() {
        return imageId;
    }
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getErrCode() {
        return errCode;
    }
    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
    public String getErrReason() {
        return errReason;
    }
    public void setErrReason(String errReason) {
        this.errReason = errReason;
    }
}
