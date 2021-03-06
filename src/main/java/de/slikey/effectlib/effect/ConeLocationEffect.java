package de.slikey.effectlib.effect;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.util.MathUtils;
import de.slikey.effectlib.util.ParticleEffect;
import de.slikey.effectlib.util.RandomUtils;
import de.slikey.effectlib.util.VectorUtils;

public class ConeLocationEffect extends LocationEffect {

	/**
	 * ParticleType of spawned particle
	 */
	public ParticleEffect particle = ParticleEffect.FLAME;

	/**
	 * Growing per iteration in the lenght (0.05)
	 */
	public float lenghtGrow = .05f;

	/**
	 * Radials per iteration to spawn the next particle (PI / 16)
	 */
	public double angularVelocity = Math.PI / 16;

	/**
	 * Cone-particles per interation (10)
	 */
	public int particles = 10;

	/**
	 * Growth in blocks per iteration on the radius (0.006)
	 */
	public float radiusGrow = 0.006f;

	/**
	 * Conesize in particles per cone
	 */
	public int particlesCone = 180;

	/**
	 * Start-angle or rotation of the cone
	 */
	public double rotation = 0;

	/**
	 * Randomize every cone on creation (false)
	 */
	public boolean randomize = false;

	/**
	 * Current step. Works as counter
	 */
	protected int step = 0;

	public ConeLocationEffect(EffectManager effectManager, Location location) {
		super(effectManager, location);
		type = EffectType.REPEATING;
		period = 1;
		iterations = 200;
	}

	@Override
	public void onRun() {
		for (int x = 0; x < particles; x++) {
			if (step > particlesCone)
				step = 0;
			if (randomize && step == 0)
				rotation = RandomUtils.getRandomAngle();
			double angle = step * angularVelocity + rotation;
			float radius = step * radiusGrow;
			float lenght = step * lenghtGrow;
			Vector v = new Vector(Math.cos(angle) * radius, lenght, Math.sin(angle) * radius);
			VectorUtils.rotateAroundAxisX(v, (location.getPitch() + 90) * MathUtils.degreesToRadians);
			VectorUtils.rotateAroundAxisY(v, -location.getYaw() * MathUtils.degreesToRadians);

			location.add(v);
			particle.display(location,visibleRange, 0, 0, 0, 0, 0);
			location.subtract(v);
			step++;
		}
	}
}
