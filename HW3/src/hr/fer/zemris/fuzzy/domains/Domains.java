package hr.fer.zemris.fuzzy.domains;

import hr.fer.zemris.fuzzy.Domain;
import hr.fer.zemris.fuzzy.IDomain;

public class Domains {

	public static final IDomain DISTANCE_DOMAIN = Domain.intRange(0, 1301);
	
	public static final IDomain SPEED_DOMAIN = Domain.intRange(0, 500);
	
	public static final IDomain ACCELERATION_DOMAIN = Domain.intRange(-40, 40);
	
	public static final IDomain TURN_DOMAIN = Domain.intRange(-90, 91);
	
}
