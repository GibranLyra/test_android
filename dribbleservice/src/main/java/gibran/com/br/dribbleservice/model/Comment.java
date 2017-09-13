package gibran.com.br.dribbleservice.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            Comment var = new Comment();
            var.likes_count = source.readInt();
            var.likes_url = source.readString();
            var.updated_at = source.readString();
            var.created_at = source.readString();
            var.id = source.readInt();
            var.body = source.readString();
            var.user = source.readParcelable(User.class.getClassLoader());
            return var;
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
    private int likes_count;
    private String likes_url;
    private String updated_at;
    private String created_at;
    private int id;
    private String body;
    private User user;

    public int getLikes_count() {
        return this.likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public String getLikes_url() {
        return this.likes_url;
    }

    public void setLikes_url(String likes_url) {
        this.likes_url = likes_url;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.likes_count);
        dest.writeString(this.likes_url);
        dest.writeString(this.updated_at);
        dest.writeString(this.created_at);
        dest.writeInt(this.id);
        dest.writeString(this.body);
        dest.writeParcelable(this.user, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
