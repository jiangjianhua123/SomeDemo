package netty;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-06 16:26
 **/
public class Message {

    int content_length;
    char protocol_flag = '1';
    byte[] version = {9, -2, 3, 4, 5};
    char message_flag = '2';
    int command = 0x0000C10D;
    byte[] message_id = new byte[32];
    String jsonStr;
    int jsonStrLeg;
    byte[] file_byte;

    public int getContent_length() {
        return content_length;
    }

    public void setContent_length(int content_length) {
        this.content_length = content_length;
    }

    public char getProtocol_flag() {
        return protocol_flag;
    }

    public void setProtocol_flag(char protocol_flag) {
        this.protocol_flag = protocol_flag;
    }

    public byte[] getVersion() {
        return version;
    }

    public void setVersion(byte[] version) {
        this.version = version;
    }

    public char getMessage_flag() {
        return message_flag;
    }

    public void setMessage_flag(char message_flag) {
        this.message_flag = message_flag;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public byte[] getMessage_id() {
        return message_id;
    }

    public void setMessage_id(byte[] message_id) {
        this.message_id = message_id;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public int getJsonStrLeg() {
        return jsonStrLeg;
    }

    public void setJsonStrLeg(int jsonStrLeg) {
        this.jsonStrLeg = jsonStrLeg;
    }

    public byte[] getFile_byte() {
        return file_byte;
    }

    public void setFile_byte(byte[] file_byte) {
        this.file_byte = file_byte;
    }
}
