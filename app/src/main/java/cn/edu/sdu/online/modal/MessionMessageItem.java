package cn.edu.sdu.online.modal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MessionMessageItem {
	public String uuid;
	public String msg;
	public int createTime;
	public int status;// 0代表没有被标记，1代表被标记了，标记了之后第二天就会被删除掉

	public MessionMessageItem() {
		uuid = createId();
		createTime = createTime();
		this.status = 0;
	}

	// 生成id
	private String createId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	// 生成创造时间
	private int createTime() {
		SimpleDateFormat format = new SimpleDateFormat("MMdd");
		Date date = new Date();
		return Integer.parseInt(format.format(date));
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getCreateTime() {
		return createTime;
	}

	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
