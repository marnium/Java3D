package java3d;

import javax.media.j3d.*;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;

public class Java3D extends javax.swing.JFrame {
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) throws Exception {
        Java3D java3d = new Java3D();
        java3d.setVisible(true);
    }

    public Java3D() {
        super("Java 3D");

        Canvas3D canvas3d = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        add(canvas3d);

        BranchGroup bgrRoot = createRootGraph();
        bgrRoot.compile();

        SimpleUniverse smpU = new SimpleUniverse(canvas3d);
        smpU.getViewingPlatform().setNominalViewingTransform();
        smpU.addBranchGraph(bgrRoot);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private BranchGroup createRootGraph() {
        BranchGroup bgrRoot = new BranchGroup();

        bgrRoot.addChild(createGeometry());
        bgrRoot.addChild(createBackground());

        return bgrRoot;
    }

    private TransformGroup createGeometry() {
        TransformGroup tgrGeo = new TransformGroup();

        int flags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        Cylinder cylinder = new Cylinder(0.2f, 0.9f, flags, createAppearence());
        tgrGeo.addChild(cylinder);
        tgrGeo.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        tgrGeo.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        //rotar
        MouseRotate msrGeo = new MouseRotate();
        msrGeo.setTransformGroup(tgrGeo);
        msrGeo.setSchedulingBounds(new BoundingSphere());
        tgrGeo.addChild(msrGeo);
        //zoom
        MouseWheelZoom mhzGeom = new MouseWheelZoom();
        mhzGeom.setTransformGroup(tgrGeo);
        mhzGeom.setSchedulingBounds(new BoundingSphere());
        tgrGeo.addChild(mhzGeom);

        return tgrGeo;
    }

    private TransformGroup createBackground() {
        TransformGroup tgrFondo = new TransformGroup();
        Background bkgFondo = new Background();
        bkgFondo.setColor(0.0f, 0.4f, 0.6f);
        bkgFondo.setApplicationBounds(new BoundingBox());
        tgrFondo.addChild(bkgFondo);

        return tgrFondo;
    }

    private Appearance createAppearence() {
        TextureLoader loader = new TextureLoader(getClass().getResource("img/textura.jpg"),
            "INTENSITY", new java.awt.Container());
        Texture texture = loader.getTexture();
        Appearance apariencia = new Appearance();
        apariencia.setTexture(texture);
        apariencia.setMaterial(new Material());

        return apariencia;
    } 
}