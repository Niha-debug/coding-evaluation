package com.aa.act.interview.org;

import java.util.Optional;

public abstract class Organization {

	private Position root;

	private static int id;

	public Organization() {
		root = createOrganization();
	}

	protected abstract Position createOrganization();

	/**
	 * hire the given person as an employee in the position that has that title
	 *
	 * @param person
	 * @param title
	 * @return the newly filled position or empty if no position has that title
	 */
	public Optional<Position> hire(Name person, String title) {
		addEmployee(root, person, title);
		plugDetails(root, person, title);
		return Optional.of(root);
	}

	private void addEmployee(Position position, Name person, String title) {
		if (position.getTitle().equals(title)) {
			position.setEmployee(Optional.of(new Employee(++id, new Name(person.getFirst(), person.getLast()))));
		}

	}
	private void plugDetails(Position pos, Name person, String title) {
		for (Position p : pos.getDirectReports()) {
			addEmployee(p, person, title);
			plugDetails(p, person, title);
		}
	}

	@Override
	public String toString() {
		return printOrganization(root, "");
	}

	private String printOrganization(Position pos, String prefix) {
		StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
		for (Position p : pos.getDirectReports()) {
			sb.append(printOrganization(p, prefix + "\t"));
		}
		return sb.toString();
	}
}