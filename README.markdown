Gradient Text for Android made easy.

Declaratively in XML:

    <?xml version="1.0" encoding="utf-8"?>
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:orientation="vertical" android:layout_width="fill_parent"
        android:layout_height="fill_parent">
        <GradientTextView
        android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:startColor="#FF000000"
            android:endColor="#FFFFFFFF"
            android:angle="90"
            android:textSize="72sp"
            android:text="HellO!!!!!"
            android:layout_centerInParent="true"/>
    </RelativeLayout>

![preview](https://github.com/koush/Widgets/raw/master/gradient.png)