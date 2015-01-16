package net.coding.program.maopao.item;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.coding.program.R;
import net.coding.program.common.CustomDialog;
import net.coding.program.common.Global;
import net.coding.program.common.HtmlContent;
import net.coding.program.maopao.MaopaoListFragment;
import net.coding.program.model.Maopao;

/**
 * Created by chaochen on 15/1/14.
 */
class CommentItem {

    private TextView comment;
    private TextView name;
    private TextView time;
    private View layout;

    public CommentItem(View convertView, View.OnClickListener onClickComment, int i) {
        layout = convertView;
        layout.setOnClickListener(onClickComment);
        name = (TextView) convertView.findViewById(R.id.name);
        time = (TextView) convertView.findViewById(R.id.time);
        comment = (TextView) convertView.findViewById(R.id.comment);
        comment.setMovementMethod(LinkMovementMethod.getInstance());
        comment.setOnClickListener(onClickComment);
        comment.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setItems(R.array.message_action_text_copy, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Global.copy(((TextView) v).getText().toString(), v.getContext());
                            Toast.makeText(v.getContext(), "已复制", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                AlertDialog dialog = builder.show();
                CustomDialog.dialogTitleLineColor(v.getContext(), dialog);
                return true;
            }
        });
    }

    public void setContent(Maopao.Comment commentData, Html.ImageGetter imageGetter, Html.TagHandler tagHandler) {
        layout.setTag(MaopaoListFragment.TAG_COMMENT, commentData);
        comment.setTag(MaopaoListFragment.TAG_COMMENT, commentData);

        name.setText(commentData.owner.name);
        time.setText(Global.dayToNow(commentData.created_at));
        Global.MessageParse parse = HtmlContent.parseMessage(commentData.content);
        comment.setText(Global.changeHyperlinkColor(parse.text, imageGetter, tagHandler));
    }

    public void setVisibility(int visibility) {
        layout.setVisibility(visibility);
    }
}