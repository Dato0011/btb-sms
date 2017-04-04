package com.bitblocker.messenger.ui.search;

import android.view.View;

import com.bitblocker.messenger.R;
import com.bitblocker.messenger.ui.base.ClickyViewHolder;
import com.bitblocker.messenger.ui.base.QKActivity;
import com.bitblocker.messenger.ui.view.AvatarView;
import com.bitblocker.messenger.ui.view.QKTextView;

public class SearchViewHolder extends ClickyViewHolder<SearchData> {

    protected View root;
    protected AvatarView avatar;
    protected QKTextView name;
    protected QKTextView date;
    protected QKTextView snippet;

    public SearchViewHolder(QKActivity context, View view) {
        super(context, view);

        root = view;
        avatar = (AvatarView) view.findViewById(R.id.search_avatar);
        name = (QKTextView) view.findViewById(R.id.search_name);
        date = (QKTextView) view.findViewById(R.id.search_date);
        snippet = (QKTextView) view.findViewById(R.id.search_snippet);
    }
}
