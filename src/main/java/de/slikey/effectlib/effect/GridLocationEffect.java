package de.slikey.effectlib.effect;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.util.ParticleEffect;
import de.slikey.effectlib.util.VectorUtils;

public class GridLocationEffect extends LocationEffect {

	/**
	 * ParticleType of the nucleus
	 */
	public ParticleEffect particle = ParticleEffect.FLAME;

	/**
	 * Rows of the grid
	 */
	public int rows = 5;
	
	/**
	 * Columns of the grid
	 */
	public int columns = 10;
	
	/**
	 * Width per cell in blocks
	 */
	public float widthCell = 1;
	
	/**
	 * Height per cell in blocks
	 */
	public float heightCell = 1;
	
	/**
	 * Particles to be spawned on the horizontal borders of the cell
	 */
	public int particlesWidth = 4;
	
	/**
	 * Particles to be spawned on the vertical borders of the cell
	 */
	public int particlesHeight = 3;
	
	/**
	 * Rotation around the Y-axis
	 */
	public double rotation = 0;

	public GridLocationEffect(EffectManager effectManager, Location location) {
		super(effectManager, location);
		type = EffectType.INSTANT;
		period = 5;
		iterations = 50;
	}

	@Override
	public void onRun() {
		// Draw rows
		Vector v = new Vector();
		for (int i = 0; i <= (rows + 1); i++) {
			for (int j = 0; j < particlesWidth * (columns + 1); j++) {
				v.setY(i * heightCell);
				v.setX(j * widthCell / particlesWidth);
				addParticle(v);
			}
		}
		// Draw columns
		for (int i = 0; i <= (columns + 1); i++) {
			for (int j = 0; j < particlesHeight * (rows + 1); j++) {
				v.setX(i * widthCell);
				v.setY(j * heightCell / particlesHeight);
				addParticle(v);
			}
		}
	}

	protected void addParticle(Vector v) {
		v.setZ(0);
		VectorUtils.rotateAroundAxisY(v, rotation);
		location.add(v);
		particle.display(location, visibleRange, 0, 0, 0, 0, 0);
		location.subtract(v);
	}

}
