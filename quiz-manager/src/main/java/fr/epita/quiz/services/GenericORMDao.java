/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.quiz.services;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.epita.quiz.datamodel.Question;

/**
 * <h3>Description</h3>
 * <p>This class allows to ...</p>
 *
 * <h3>Usage</h3>
 * <p>This class should be used as follows:
 *   <pre><code>${type_name} instance = new ${type_name}();</code></pre>
 * </p>
 *
 * @since $${version}
 * @see See also $${link}
 * @author ${user}
 *
 * ${tags}
 */
public abstract class GenericORMDao<T> {

	@Inject
	SessionFactory sf;

	public final void create(T entity) {
		if (!beforeCreate(entity)) {
			return;
		}

		final Session session = sf.openSession();
		final Transaction tx = session.beginTransaction();
		session.saveOrUpdate(entity);
		tx.commit();

	}

	protected boolean beforeCreate(T entity) {
		return entity != null;
	}

	public final void update(T entity) {
		final Session session = sf.openSession();
		session.saveOrUpdate(entity);
	}

	public final void delete(T entity) {
		final Session session = sf.openSession();
		session.delete(entity);
	}

	@SuppressWarnings("unchecked")
	public final List<T> search(T entity) {
		final Session session = sf.openSession();
		final WhereClauseBuilder<T> wcb = getWhereClauseBuilder(entity);
		
		final Query<T> searchQuery = session.createQuery(wcb.getQueryString());
		for (final Entry<String, Object> parameterEntry : wcb.getParameters().entrySet()) {
			if (parameterEntry.getValue() == null) {
				searchQuery.setParameter(parameterEntry.getKey(), "");
			}
			searchQuery.setParameter(parameterEntry.getKey(), parameterEntry.getValue());
		}

		return searchQuery.list();
	}
	
	protected abstract String getQuery();

	protected WhereClauseBuilder<T> getWhereClauseBuilder(T entity) {
		final WhereClauseBuilder<T> wcb = new WhereClauseBuilder<>();
		wcb.setQueryString(getQuery());

		// TODO as bonus : let the whereclausebuilder generate this map thanks to introspection
		final Map<String, Object> parameters = new LinkedHashMap<>();
		Field[] fields = entity.getClass().getDeclaredFields();
		for (Field field: fields) {
			field.setAccessible(true);
			try {
				if (field.getName() == "id") { continue; }
				parameters.put(field.getName(), field.get(entity));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		wcb.setParameters(parameters);
		return wcb;

	}

	// Old conception
	// protected abstract String getSearchQuery(T entity);
	//
	// protected abstract void completeQuery(T entity, Query toBeCompleted);

}
