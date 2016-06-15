/**************************************************************************
 * UserCreateFragment.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.user;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.jeanlui.demactor.R;
import com.jeanlui.demactor.entity.User;
import com.jeanlui.demactor.entity.UserGroup;
import com.jeanlui.demactor.entity.Title;

import com.jeanlui.demactor.harmony.view.HarmonyFragmentActivity;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;
import com.jeanlui.demactor.harmony.widget.DateWidget;
import com.jeanlui.demactor.harmony.widget.DateTimeWidget;
import com.jeanlui.demactor.harmony.widget.MultiEntityWidget;
import com.jeanlui.demactor.harmony.widget.SingleEntityWidget;
import com.jeanlui.demactor.harmony.widget.EnumSpinner;
import com.jeanlui.demactor.menu.SaveMenuWrapper.SaveMenuInterface;
import com.jeanlui.demactor.provider.utils.UserProviderUtils;
import com.jeanlui.demactor.provider.utils.UserGroupProviderUtils;

/**
 * User create fragment.
 *
 * This fragment gives you an interface to create a User.
 */
public class UserCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected User model = new User();

    /** Fields View. */
    /** login View. */
    protected EditText loginView;
    /** password View. */
    protected EditText passwordView;
    /** firstname View. */
    protected EditText firstnameView;
    /** lastname View. */
    protected EditText lastnameView;
    /** createdAt DateTime View. */
    protected DateTimeWidget createdAtView;
    /** birthdate Date View. */
    protected DateWidget birthdateView;
    /** The userGroup chooser component. */
    protected SingleEntityWidget userGroupWidget;
    /** The userGroup Adapter. */
    protected SingleEntityWidget.EntityAdapter<UserGroup> 
                userGroupAdapter;
    /** title View. */
    protected EnumSpinner titleView;
    /** The friends chooser component. */
    protected MultiEntityWidget friendsWidget;
    /** The friends Adapter. */
    protected MultiEntityWidget.EntityAdapter<User> 
                friendsAdapter;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.loginView =
            (EditText) view.findViewById(R.id.user_login);
        this.passwordView =
            (EditText) view.findViewById(R.id.user_password);
        this.firstnameView =
            (EditText) view.findViewById(R.id.user_firstname);
        this.lastnameView =
            (EditText) view.findViewById(R.id.user_lastname);
        this.createdAtView =
                (DateTimeWidget) view.findViewById(R.id.user_createdat);
        this.birthdateView =
                (DateWidget) view.findViewById(R.id.user_birthdate);
        this.userGroupAdapter = 
                new SingleEntityWidget.EntityAdapter<UserGroup>() {
            @Override
            public String entityToString(UserGroup item) {
                return String.valueOf(item.getId());
            }
        };
        this.userGroupWidget =
            (SingleEntityWidget) view.findViewById(R.id.user_usergroup_button);
        this.userGroupWidget.setAdapter(this.userGroupAdapter);
        this.userGroupWidget.setTitle(R.string.user_usergroup_dialog_title);
        this.titleView =
            (EnumSpinner) view.findViewById(R.id.user_title);
        this.titleView.setEnum(Title.class);
        this.friendsAdapter = 
                new MultiEntityWidget.EntityAdapter<User>() {
            @Override
            public String entityToString(User item) {
                return String.valueOf(item.getId());
            }
        };
        this.friendsWidget =
            (MultiEntityWidget) view.findViewById(R.id.user_friends_button);
        this.friendsWidget.setAdapter(this.friendsAdapter);
        this.friendsWidget.setTitle(R.string.user_friends_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        if (this.model.getLogin() != null) {
            this.loginView.setText(this.model.getLogin());
        }
        if (this.model.getPassword() != null) {
            this.passwordView.setText(this.model.getPassword());
        }
        if (this.model.getFirstname() != null) {
            this.firstnameView.setText(this.model.getFirstname());
        }
        if (this.model.getLastname() != null) {
            this.lastnameView.setText(this.model.getLastname());
        }
        if (this.model.getCreatedAt() != null) {
            this.createdAtView.setDateTime(this.model.getCreatedAt());
        }
        if (this.model.getBirthdate() != null) {
            this.birthdateView.setDate(this.model.getBirthdate());
        }
        if (this.model.getTitle() != null) {
            this.titleView.setSelectedItem(this.model.getTitle());
        }

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setLogin(this.loginView.getEditableText().toString());

        this.model.setPassword(this.passwordView.getEditableText().toString());

        this.model.setFirstname(this.firstnameView.getEditableText().toString());

        this.model.setLastname(this.lastnameView.getEditableText().toString());

        this.model.setCreatedAt(this.createdAtView.getDateTime());

        this.model.setBirthdate(this.birthdateView.getDate());

        this.model.setUserGroup(this.userGroupAdapter.getSelectedItem());

        this.model.setTitle((Title) this.titleView.getSelectedItem());

        this.model.setFriends(this.friendsAdapter.getCheckedItems());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.loginView.getText().toString().trim())) {
            error = R.string.user_login_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.passwordView.getText().toString().trim())) {
            error = R.string.user_password_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.lastnameView.getText().toString().trim())) {
            error = R.string.user_lastname_invalid_field_error;
        }
        if (this.createdAtView.getDateTime() == null) {
            error = R.string.user_createdat_invalid_field_error;
        }
        if (this.birthdateView.getDate() == null) {
            error = R.string.user_birthdate_invalid_field_error;
        }
        if (this.userGroupAdapter.getSelectedItem() == null) {
            error = R.string.user_usergroup_invalid_field_error;
        }
        if (this.friendsAdapter.getCheckedItems().isEmpty()) {
            error = R.string.user_friends_invalid_field_error;
        }
    
        if (error > 0) {
            Toast.makeText(this.getActivity(),
                this.getActivity().getString(error),
                Toast.LENGTH_SHORT).show();
        }
        return error == 0;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(
                R.layout.fragment_user_create,
                container,
                false);

        this.initializeComponent(view);
        this.loadData();
        return view;
    }

    /**
     * This class will save the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class CreateTask extends AsyncTask<Void, Void, Uri> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to persist. */
        private final User entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final UserCreateFragment fragment,
                final User entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.user_progress_save_title),
                    this.ctx.getString(
                            R.string.user_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new UserProviderUtils(this.ctx).insert(
                        this.entity);

            return result;
        }

        @Override
        protected void onPostExecute(Uri result) {
            super.onPostExecute(result);
            if (result != null) {
                final HarmonyFragmentActivity activity =
                                         (HarmonyFragmentActivity) this.ctx;
                activity.finish();
            } else {
                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(this.ctx);
                builder.setIcon(0);
                builder.setMessage(
                        this.ctx.getString(
                                R.string.user_error_create));
                builder.setPositiveButton(
                        this.ctx.getString(android.R.string.yes),
                        new Dialog.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int which) {

                            }
                        });
                builder.show();
            }

            this.progress.dismiss();
        }
    }

    /**
     * This class will save the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class LoadTask extends AsyncTask<Void, Void, Void> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Progress Dialog. */
        private ProgressDialog progress;
        /** Fragment. */
        private UserCreateFragment fragment;
        /** userGroup list. */
        private ArrayList<UserGroup> userGroupList;
        /** friends list. */
        private ArrayList<User> friendsList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final UserCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.user_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.user_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.userGroupList = 
                new UserGroupProviderUtils(this.ctx).queryAll();
            this.friendsList = 
                new UserProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.userGroupAdapter.loadData(this.userGroupList);
            this.fragment.friendsAdapter.loadData(this.friendsList);
            this.progress.dismiss();
        }
    }

    @Override
    public void onClickSave() {
        if (this.validateData()) {
            this.saveData();
            new CreateTask(this, this.model).execute();
        }
    }
}
