package org.ikov.client.computer;
/*
import java.util.Enumeration;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.EnumVariant;
import com.jacob.com.Variant;
*/

public class SerialNumber {
	/*
    public static String grabSerial() {
		String serial = "invalid_serial";
		ComThread.InitMTA();
		try {
			ActiveXComponent wmi = new ActiveXComponent("winmgmts:\\\\.");
			Variant instances = wmi.invoke("InstancesOf", "Win32_BaseBoard");
			Enumeration<Variant> en = new EnumVariant(instances.getDispatch());
			while (en.hasMoreElements()) {
				ActiveXComponent bb = new ActiveXComponent(en.nextElement().getDispatch());
				serial = bb.getPropertyAsString("SerialNumber");
				break;
			}
		} finally {
				ComThread.Release();
		}
		return serial;
	}
*/
}