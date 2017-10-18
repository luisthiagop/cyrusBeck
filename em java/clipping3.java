import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Canvas3D;
import javax.swing.JFrame;
import javax.vecmath.Point3d;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.LineArray;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JOptionPane;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class clipping3 extends JFrame{
    public Canvas3D canvas;
    
    public clipping3(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas= new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        SimpleUniverse uni= new SimpleUniverse(canvas);
        uni.getViewingPlatform().setNominalViewingTransform();
        createSceneGraph(uni);
        addLight(uni);
        OrbitBehavior ob = new OrbitBehavior(canvas);
        ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE));
        uni.getViewingPlatform().setViewPlatformBehavior(ob);
        setTitle("3DTest");
        setSize(800,800);
        getContentPane().add("Center", canvas);
        setVisible(true);
    }
    
    public static void main(String[] args){
        clipping3 t= new clipping3();
    }
    
    public void createSceneGraph(SimpleUniverse u){
        //Criando volume do Clipping, o volume sera um retangulo
        //aparencia do retangulo
        Color3f ambientColourBox = new Color3f(0.8f,0.0f,0.0f);
        Color3f emissiveColourBox = new Color3f(0.0f,0.0f,0.0f);
        Color3f diffuseColourBox = new Color3f(0.8f,0.0f,0.0f);
        Color3f specularColourBox = new Color3f(1.0f,0.0f,0.0f);
        float shininessBox = 10.0f;
        Appearance boxApp = new Appearance();
        boxApp.setMaterial(new Material(ambientColourBox,emissiveColourBox,diffuseColourBox,specularColourBox,shininessBox));
        //Retangulo
        Box retangulo= new Box(0.4f,0.4f,0.8f, boxApp);//(comprimentoX, comprimentoY, comprimentoZ)
        //TransformGroup: Transformgroup do retangulo e a tranformacao para mudar a posicao
        TransformGroup tgBox= new TransformGroup();
        Transform3D posicao= new Transform3D();
        posicao.setTranslation(new Vector3f(0f, 0f, -1.5f));//posicao no Z= 0.7
        tgBox.setTransform(posicao);
        tgBox.addChild(retangulo);
        
        //Linha utilizada para teste
        LineArray linha1= new LineArray(2, LineArray.COORDINATES);
        Point3f P0= new Point3f(0, -0.5f, -1f);
        Point3f P1= new Point3f(0, 0.5f, -1f);
        linha1.setCoordinate(0, P0);
        linha1.setCoordinate(1, P1);
        TransformGroup tgLine= new TransformGroup();
        tgLine.addChild(new Shape3D(linha1));
        
        //ALGORITMO
        //vetores normais: Cada face tem uma vertor normal invertido(n) que aponta para dentro da caixa. 
        Vector3d n1= new Vector3d(0, 0, -1);//face da frente
        Vector3d n2= new Vector3d(1, 0, 0);//face da esquerda
        Vector3d n3= new Vector3d(0, 0, 1);//face de tras
        Vector3d n4= new Vector3d(-1, 0, 0);//face da direita
        Vector3d n5= new Vector3d(0, -1, 0);//face de cima
        Vector3d n6= new Vector3d(0, 1, 0);//face de baixo
        //simplificação    
        Vector3d[] n= new Vector3d[6];
        n[0]= n1;//face1
        n[1]= n2; //face2
        n[2]= n3; //face3
        n[3]= n4; //face4
        n[4]= n5; //face5
        n[5]= n6; //face6
 
        //fi: -> i= 1, 2, ... , 6 -> fi é um ponto que pertence ao plano 2D infinito formado pela face i do retangulo. 
        Point3f[] f= new Point3f[6];
        f[0]= new Point3f(-retangulo.getXdimension(), -retangulo.getYdimension(), -0.7f);//f1= f2 = f6 (aresta no canto inferior esquerdo, compartilhada pelas faces 1, 2 e 6)
        f[1]= new Point3f(-retangulo.getXdimension(), -retangulo.getYdimension(), -0.7f);
        f[2]= new Point3f(retangulo.getXdimension(), retangulo.getYdimension(), -(retangulo.getZdimension()+1.5f));//f3 = f4 = f5 (aresta no canto superior direto da parte de tras)
        f[3]= new Point3f(retangulo.getXdimension(), retangulo.getYdimension(), -(retangulo.getZdimension()+1.5f));
        f[4]= new Point3f(retangulo.getXdimension(), retangulo.getYdimension(), -(retangulo.getZdimension()+1.5f));
        f[5]= new Point3f(-retangulo.getXdimension(), -retangulo.getYdimension(), -0.7f);
     
        //Calculo do vetor D: -> D= (P1 - P0) -> Diretriz da reta
        Vector3d D= new Vector3d(P1.x - P0.x, P1.y - P0.y, P1.z - P0.z);
     
        //Calculo do vetor Wi: -> Wi= (P0 - fi) -> Vetor indo do ponto no plano(fi) até o inicio da reta(P0)
        Vector3d W1= new Vector3d(P0.x - f[0].x, P0.y - f[0].y, P0.z - f[0].z);//W1 = W2 = W6
        Vector3d W2= new Vector3d(P0.x - f[1].x, P0.y - f[1].y, P0.z - f[1].z);
        Vector3d W3= new Vector3d(P0.x - f[2].x, P0.y - f[2].y, P0.z - f[2].z);//W3 = W4 = W5
        Vector3d W4= new Vector3d(P0.x - f[3].x, P0.y - f[3].y, P0.z - f[3].z);
        Vector3d W5= new Vector3d(P0.x - f[4].x, P0.y - f[4].y, P0.z - f[4].z);
        Vector3d W6= new Vector3d(P0.x - f[5].x, P0.y - f[5].y, P0.z - f[5].z);
        //simplificação
        Vector3d[] W= new Vector3d[6];
        W[0]= W1;
        W[1]= W2;  
        W[2]= W3;
        W[3]= W4;
        W[4]= W5;
        W[5]= W6;
     
        //T->[0, 1]
        //t é o valor paramétrico da reta, onde o ponto Pi é definido como: Pi= P0 + (P1 - P0)t 
        //se varia t de 0 a 1 percorre-se todos os pontos da reta.
        double tL= 0; //Valor de t no inicio da reta, onde o ponto é P0.
        double tU= 1; //Valor de t no fim a reta, onde o ponto é P1.
        double t= 0;
        int i;
        //Calculo:
        for(i= 0; i< 6; i++){
            //vD= (ni.D) -> Produto Escalar entre o vetor normal da face i(ni) e o vetor que representa a reta(D) 
            double vD= n[i].dot(D);
            //vW= (ni.Wi) -> Produto Escalar entre o vetor normal da face i(ni) e o vetor que aponta do ponto da face i(fi) para o inicio da reta(P0)
            double vW= n[i].dot(W[i]);
            if(vD== 0){//Verifica se a reta é paralela a face i  
                continue; //i++ -> Se Sim, vai para próxima face
            }
            else{
            //t= -(vW/vD) -> Calculo de t, (valor de parametrização da reta onde o plano encontrará com a reta -> t[0, 1])
                t= -(vW/vD);
                if(vD> 0){//se D estiver apontando para dentro da face
                    if(t> 1){//Se W estiver apontando para fora da face e for maior que D
                        JOptionPane.showMessageDialog(null,"Segmento Rejeitado");
                        break;//sai do for
                    }
                    else{
                        tL= Math.max(t, tL);// aumentar o valor de tL, ou seja, diminui na reta; i++ -> testa proxima face
                    }
                }
                else{//se D estiver apontado para fora da face
                    if(t< 0){//se W, tambem, estiver apontado para fora da face
                        JOptionPane.showMessageDialog(null,"Segmento Rejeitado");
                        break;
                    }
                    else{//W esta apontando para dentro da face
                        tU= Math.min(t, tU);// diminui o valor de tU, dimuindo na reta; ++ -> testa proxima face
                    }
                }
            }
        }
        if(tL>= tU){//Reta curtas próximas das faces, por exemplo.
          JOptionPane.showMessageDialog(null,"Segmento Rejeitado");
        }     
        else if(i== 6){// Verificação das 6 faces
          JOptionPane.showMessageDialog(null," A Reta será Desenhada");
          //cria nova reta que representa o reta clipada
          LineArray linha2= new LineArray(2, LineArray.COORDINATES);
          Point3d P0_clipp= new Point3d();
          //Atualizando os pontos de acordo com tL para P0 e tU para P1
          P0_clipp.setX((double)(P0.x +(P1.x - P0.x)*tL)); 
          P0_clipp.setY((double)(P0.y +(P1.y - P0.y)*tL));
          P0_clipp.setZ((double)(P0.z +(P1.z - P0.z)*tL)+0.8);
          Point3d P1_clipp= new Point3d();
          P1_clipp.setX((double)(P0.x +(P1.x - P0.x)*tU));
          P1_clipp.setY((double)(P0.y +(P1.y - P0.y)*tU));
          P1_clipp.setZ((double)(P0.z +(P1.z - P0.z)*tU)+0.8);
          linha2.setCoordinate(0, P0_clipp);
          linha2.setCoordinate(1, P1_clipp);
          tgLine.addChild(new Shape3D(linha2));
        }
        //FIM_ALGORITMO
        
        //criando a raiz do Scenegraph
        BranchGroup theScene = new BranchGroup();
        //adidionando os TransformGroup do retangulo e da linha
        theScene.addChild(tgBox);
        theScene.addChild(tgLine);
        //Gerar um fundo e adicionar na cena.
        Background bg = new Background(new Color3f(0.0f,0.0f,0.0f));
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
        bg.setApplicationBounds(bounds);
        theScene.addChild(bg);
        //compilando a cena
        theScene.compile();
        //Adicione tudo ao universo.
        u.addBranchGraph(theScene);
    }
    
    public void addLight(SimpleUniverse u){
        //adicinando luz ambiente
        BranchGroup bgLight = new BranchGroup();
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), Double.MAX_VALUE);
        Color3f lightColour4 = new Color3f(0.6f, 0.6f, 0.6f); 
        AmbientLight light4 = new AmbientLight(lightColour4);
        light4.setInfluencingBounds(bounds);
        bgLight.addChild(light4);
        //adicionando luz Pontual
        Color3f lightColour2 = new Color3f(0.3f, 0.3f, 0.3f);
        PointLight light2 = new PointLight(lightColour2, new Point3f(1.0f,1.0f,1.0f), new Point3f(0.2f,0.01f,0.01f));
        light2.setInfluencingBounds(bounds);
        bgLight.addChild(light2);
        //adicionando o branchgroup ao universo
        u.addBranchGraph(bgLight);
    }
}
