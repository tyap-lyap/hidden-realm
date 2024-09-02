package ru.pinkgoosik.hiddenrealm.extension;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public interface PlayerExtension {

	void savePrevPosition();
	void teleportToPrevPosition();
	void resetPrevPosition();
	PlayerPosition getPrevPosition();
	void setPrevPosition(PlayerPosition pos);

	class PlayerPosition {
		public Vec3d pos;
		public float yaw;
		public float pitch;
		public Identifier dimension;

		public PlayerPosition(Vec3d pos, float yaw, float pitch, Identifier dimension) {
			this.pos = pos;
			this.yaw = yaw;
			this.pitch = pitch;
			this.dimension = dimension;
		}

		public PlayerPosition copy() {
			return new PlayerPosition(this.pos, this.yaw, this.pitch, this.dimension);
		}
	}
}
