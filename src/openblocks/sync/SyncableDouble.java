package openblocks.sync;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;

public class SyncableDouble implements ISyncableObject {

	private double value;
	private boolean dirty = false;

	public SyncableDouble(double value) {
		this.value = value;
	}

	public SyncableDouble() {
		this(0.0f);
	}

	public void setValue(double newValue) {
		if (newValue != value) {
			value = newValue;
			markDirty();
		}
	}

	public double getValue() {
		return value;
	}

	@Override
	public void readFromStream(DataInputStream stream) throws IOException {
		value = stream.readDouble();
	}

	@Override
	public void writeToStream(DataOutputStream stream, boolean fullData)
			throws IOException {
		stream.writeDouble(value);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag, String name) {
		tag.setDouble(name, value);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag, String name) {
		if (tag.hasKey(name)) {
			value = tag.getDouble(name);
		}
	}

	public void modify(float by) {
		setValue(value + by);
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	@Override
	public void markClean() {
		dirty = false;
	}

	@Override
	public void markDirty() {
		dirty = true;
	}

	@Override
	public void resetChangeTimer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
}
