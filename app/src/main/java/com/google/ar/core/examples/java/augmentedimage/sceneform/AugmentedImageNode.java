
package com.google.ar.core.examples.java.augmentedimage.sceneform;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.google.ar.core.AugmentedImage;
import com.google.ar.core.examples.java.WebViewAct;
import com.google.ar.core.examples.java.augmentedimage.R;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ViewRenderable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AugmentedImageNode extends AnchorNode implements View.OnClickListener {

    private AugmentedImage image;
    private Context context;
    private ViewRenderable renderableView;

    public AugmentedImageNode(Context context) {
        this.context = context;

        CompletableFuture<ViewRenderable> viewCompFuture =
                ViewRenderable.builder().setView(context, R.layout.layout_renderable).build();

        CompletableFuture.allOf(viewCompFuture)
                .handle((notUsed, throwable) -> {
                    try {
                        renderableView = viewCompFuture.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }


                    return null;
                });
    }

    public void setImage(AugmentedImage image) {
        this.image = image;

        CompletableFuture<ViewRenderable> viewCompFuture =
                ViewRenderable.builder().setView(context, R.layout.layout_renderable).build();

        CompletableFuture.allOf(viewCompFuture)
                .handle((notUsed, throwable) -> {
                    try {
                        renderableView = viewCompFuture.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }


                    return null;
                });

        setAnchor(image.createAnchor(image.getCenterPose()));

        Node solarControls = new Node();
        solarControls.setParent(this);
        solarControls.setLocalPosition(new Vector3(0.0f, 0.0f, 0.0f));
        solarControls.setRenderable(renderableView);

        View renderableLayout = renderableView.getView();

        listeners(renderableLayout);

    }

    private void listeners(View renderableLayout) {
        renderableLayout.findViewById(R.id.ivContactUs).setOnClickListener(this);
        renderableLayout.findViewById(R.id.ivYudiz).setOnClickListener(this);
        renderableLayout.findViewById(R.id.ivLinkedIn).setOnClickListener(this);
    }

    public AugmentedImage getImage() {
        return image;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivYudiz:
                openWebAct(context.getString(R.string.yudiz_site_url));
                break;
            case R.id.ivContactUs:
                openMail();
                break;
            case R.id.ivLinkedIn:
                openWebAct(context.getString(R.string.yudiz_linked_in_url));
                break;
        }
    }

    private void openWebAct(String url) {
        context.startActivity(new Intent(context, WebViewAct.class).putExtra(context.getString(R.string.int__web_view_act__url), url));
    }

    private void openMail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", context.getString(R.string.yudiz_mail_address), null));
        context.startActivity(Intent.createChooser(emailIntent, context.getString(R.string.choose)));
    }
}
