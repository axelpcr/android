<@header?interpret />
package ${project_namespace}.harmony.view;

import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import ${project_namespace}.menu.${project_name?cap_first}Menu;

/**
 * Harmony custom list Fragment.
 * @param <T> Type to show
 */
public abstract class HarmonyListFragment<T> extends SherlockListFragment
implements LoaderManager.LoaderCallbacks<Cursor> {
	/**
	 * Recall internal address (Hack Micky).
	 */
	protected static final int INTERNAL_EMPTY_ID = 0x00ff0001;
	/** progress container ID. */
	protected static final int INTERNAL_PROGRESS_CONTAINER_ID = 0x00ff0002;
	/** list container ID. */
	protected static final int INTERNAL_LIST_CONTAINER_ID = 0x00ff0003;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setHasOptionsMenu(true);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.clear();

		try {
			${project_name?cap_first}Menu.getInstance(this.getActivity(), this)
										  .updateMenu(menu, this.getActivity());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result;
		try {
			result = ${project_name?cap_first}Menu.getInstance(
			this.getActivity(),
			this).dispatch(item, this.getActivity());
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			${project_name?cap_first}Menu.getInstance(this.getActivity(), this)
			.onActivityResult(requestCode, resultCode, data, this.getActivity(),
			this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}


	/**
	 * Initialize Custom List Fragment.
	 * @param rootView The rootview
	 * @param progressLayoutId The Progress Layout id
	 * @param listContainerId The list container id
	 */
	protected void initializeHackCustomList(final View rootView,
			int progressLayoutId,
			int listContainerId) {
		// HACK Micky : Map component support ListFragment
		// Progress
		final LinearLayout progressLayout =
				(LinearLayout) rootView.findViewById(
						progressLayoutId);
		progressLayout.setId(INTERNAL_PROGRESS_CONTAINER_ID);

		// Empty
		final TextView emptyText =
				(TextView) rootView.findViewById(android.R.id.empty);
		emptyText.setId(INTERNAL_EMPTY_ID);

		// ListContainer
		final RelativeLayout listContainer =
				(RelativeLayout) rootView.findViewById(
						listContainerId);
		listContainer.setId(INTERNAL_LIST_CONTAINER_ID);
		// END HACK
	}

}
