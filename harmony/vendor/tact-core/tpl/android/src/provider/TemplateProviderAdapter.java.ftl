<#assign curr = entities[current_entity] />
<@header?interpret />
package ${local_namespace};

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ${local_namespace}.base.${curr.name?cap_first}ProviderAdapterBase;

/**
 * ${curr.name?cap_first}ProviderAdapter.
 */
public class ${curr.name?cap_first}ProviderAdapter
					extends ${curr.name?cap_first}ProviderAdapterBase {

	/**
	 * Constructor.
	 * @param ctx context
	 */
	public ${curr.name?cap_first}ProviderAdapter(final Context ctx) {
		this(ctx, null);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public ${curr.name?cap_first}ProviderAdapter(final Context ctx,
												 final SQLiteDatabase db) {
		super(ctx, db);
	}
}

