package ru.pinkgoosik.hiddenrealm.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;

public class BazaarPortalParticle extends SpriteBillboardParticle {
	private final SpriteProvider sprites;

	public BazaarPortalParticle(ClientWorld world, double x, double y, double z, double color, SpriteProvider sprites) {
		super(world, x, y, z, 0, 0, 0);
		this.maxAge = 10;
		this.setVelocity(0D, 0D, 0D);
		if(color != 0) this.setColor((int) color);
		this.scale = 0.3F;
		this.sprites = sprites;
		this.setSpriteForAge(sprites);
	}

	public void setColor(int rgbHex) {
		float red = (float) ((rgbHex & 16711680) >> 16) / 255.0F;
		float green = (float) ((rgbHex & '\uff00') >> 8) / 255.0F;
		float blue = (float) ((rgbHex & 255)) / 255.0F;
		this.setColor(red, green, blue);
	}

	@Override
	public void tick() {
        if(this.age > this.maxAge / 2) {
            this.setAlpha(1.0F - ((float) this.age - (float) (this.maxAge / 2)) / (float) this.maxAge);
        }
		if(this.age++ >= this.maxAge) {
			this.markDead();
		}
		else {
			this.setSpriteForAge(sprites);
		}
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}

	public record Factory(SpriteProvider sprites) implements ParticleFactory<SimpleParticleType> {
		public Particle createParticle(SimpleParticleType type, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
			return new BazaarPortalParticle(world, x, y, z, velX, sprites);
		}
	}
}
