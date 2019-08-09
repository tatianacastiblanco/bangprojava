package com.mygdx.bang;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.HashMap;
import java.util.Map;

public class BANG extends Game{
	static float width = 1080;
	static float height = 1920;
	OrthographicCamera camera;
	OrthographicCamera stageCamera;
	FitViewport viewport;
	SpriteBatch batch;
	Stage stage;
	Texture img;
	Image transitionText;

	AssetManager assetManager;
	Image backLoad, barLoad, endBarLoad;

	HttpRequestBuilder builder;
	Net.HttpRequest request;

	DataJson dataUser;

	boolean musicOn = true;
	boolean soundOn = true;

	String textoOnline = "";
	int textOnlineActive = 0;
	
	@Override
	public void create(){
		setScreen(new SplashScreen(BANG.this));
	}

	public void checkUser(final String user){
		textOnlineActive = 1;
		Map<String, String> params = new HashMap<String, String>();
		params.put("key", "bangCheck");
		params.put("user", user);

		builder = new HttpRequestBuilder();
		request = builder.newRequest().method(Net.HttpMethods.POST).url("http://c.biu.us/bang/checkUser.php").build();
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		request.setContent(HttpParametersUtils.convertHttpParameters(params));
		request.setTimeOut(30000);

		final long start = System.nanoTime(); //for checking the time until response
		Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener(){
			@Override
			public void handleHttpResponse(Net.HttpResponse httpResponse){
				String text = httpResponse.getResultAsString();
				Gdx.app.log("WebRequest", "HTTP Response code: " + httpResponse.getStatus().getStatusCode());
				Gdx.app.log("WebRequest", "HTTP Response code2: " + text);
				Gdx.app.log("WebRequest", "Response time: " + ((System.nanoTime() - start) / 1000000) + "ms");
				textoOnline = text;
				textOnlineActive = 2;
			}

			@Override
			public void failed(Throwable t){
				Gdx.app.log("WebRequest", String.valueOf(t));
				textoOnline = "Ocurrio un error, vuelve a intentarlo.";
				textOnlineActive = 2;
			}

			@Override
			public void cancelled(){
				Gdx.app.log("WebRequest", "HTTP request cancelled");
				textoOnline = "Ocurrio un error, vuelve a intentarlo.";
				textOnlineActive = 2;
			}
		});
	}

	public void addUser(final String user, String name, final String mail, String date, String genero, String educacion, String ocupacion, String pregunta){
		textOnlineActive = 1;
		Map<String, String> params = new HashMap<String, String>();
		params.put("register", "arcunoid");
		params.put("user", user);
		params.put("name", name);
		params.put("mail", mail);
		params.put("date", date);
		params.put("genero", genero);
		params.put("educacion", educacion);
		params.put("ocupacion", ocupacion);
		params.put("pregunta", pregunta);

		builder = new HttpRequestBuilder();
		request = builder.newRequest().method(Net.HttpMethods.POST).url("http://c.biu.us/bang/register2.php").build();
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		request.setContent(HttpParametersUtils.convertHttpParameters(params));
		request.setTimeOut(30000);

		final long start = System.nanoTime(); //for checking the time until response
		Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener(){
			@Override
			public void handleHttpResponse(Net.HttpResponse httpResponse){
				String text = httpResponse.getResultAsString();
				Gdx.app.log("WebRequest", "HTTP Response code: " + httpResponse.getStatus().getStatusCode());
				Gdx.app.log("WebRequest", "HTTP Response code2: " + text);
				Gdx.app.log("WebRequest", "Response time: " + ((System.nanoTime() - start) / 1000000) + "ms");
				textoOnline = text;

				textOnlineActive = 2;
			}

			@Override
			public void failed(Throwable t){
				Gdx.app.log("WebRequest", String.valueOf(t));
				textoOnline = "Ocurrio un error, vuelve a intentarlo.";
				textOnlineActive = 2;
			}

			@Override
			public void cancelled(){
				Gdx.app.log("WebRequest", "HTTP request cancelled");
				textoOnline = "Ocurrio un error, vuelve a intentarlo.";
				textOnlineActive = 2;
			}
		});
	}

	public void addIdeas(){
		Gdx.app.log("guardar", "entre");
		boolean fileExist = Gdx.files.local("levelData/data.json").exists();
		FileHandle file = Gdx.files.local("levelData/data.json");
		Json json = new Json();
		String dataS = json.prettyPrint(dataUser);
		System.out.println(json.prettyPrint(dataUser));
		file.writeString(dataS, false);
	}

	public void addIdeasOnline(String dataS){
		Map<String, String> params = new HashMap<String, String>();
		params.put("key", "bangIdeas");
		params.put("user", dataUser.getUser());
		params.put("ideas", dataS);

		builder = new HttpRequestBuilder();
		request = builder.newRequest().method(Net.HttpMethods.POST).url("http://c.biu.us/bang/register3.php").build();
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		request.setContent(HttpParametersUtils.convertHttpParameters(params));
		request.setTimeOut(30000);

		final long start = System.nanoTime(); //for checking the time until response
		Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener(){
			@Override
			public void handleHttpResponse(Net.HttpResponse httpResponse){
				String text = httpResponse.getResultAsString();
				Gdx.app.log("WebRequest", "HTTP Response code: " + httpResponse.getStatus().getStatusCode());
				Gdx.app.log("WebRequest", "HTTP Response code2: " + text);
				Gdx.app.log("WebRequest", "Response time: " + ((System.nanoTime() - start) / 1000000) + "ms");
			}

			@Override
			public void failed(Throwable t){
				Gdx.app.log("WebRequest", String.valueOf(t));
				//textOnline = "Ocurrio un error, vuelve a intentarlo.";
			}

			@Override
			public void cancelled(){
				Gdx.app.log("WebRequest", "HTTP request cancelled");
				//textOnline = "Ocurrio un error, vuelve a intentarlo.";
			}
		});
	}

	public void setMusicOn(boolean musicon){
		musicOn = musicon;
	}

	public boolean getMusicOn(){
		return musicOn;
	}

	@Override
	public void dispose(){

	}

	public void uploadImage(String idea, String name){
		FileHandle file = Gdx.files.external("Pictures/Bangs/"+name);
		if(file.exists()){
			byte[] bytes = file.readBytes();
			Map<String, String> params = new HashMap<String, String>();
			String b64 = String.valueOf(Base64Coder.encode(bytes));
			params.put("key", "bangUploadI");
			params.put("user", dataUser.getUser());
			params.put("name", name);
			params.put("idea", idea);
			params.put("b64", b64);
			builder = new HttpRequestBuilder();
			request = builder.newRequest().method(Net.HttpMethods.POST).url("http://c.biu.us/bang/register7.php").build();
			request.setHeader("Content-Type", "application/x-www-form-urlencoded");
			request.setContent(HttpParametersUtils.convertHttpParameters(params));
			request.setTimeOut(30000);

			final long start = System.nanoTime(); //for checking the time until response
			Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener(){
				@Override
				public void handleHttpResponse(Net.HttpResponse httpResponse){
					String text = httpResponse.getResultAsString();
					Gdx.app.log("WebRequest", "HTTP Response code: " + httpResponse.getStatus().getStatusCode());
					Gdx.app.log("WebRequest", "HTTP Response code2: " + text);
					Gdx.app.log("WebRequest", "Response time: " + ((System.nanoTime() - start) / 1000000) + "ms");
				}

				@Override
				public void failed(Throwable t){
					Gdx.app.log("WebRequest", String.valueOf(t));
					//textOnline = "Ocurrio un error, vuelve a intentarlo.";
				}

				@Override
				public void cancelled(){
					Gdx.app.log("WebRequest", "HTTP request cancelled");
					//textOnline = "Ocurrio un error, vuelve a intentarlo.";
				}
			});
		}
	}

	public void setMusic(boolean value){
		musicOn = value;
	}

	public void setSound(boolean value){
		soundOn = value;
	}

	public boolean getMusic(){
		return musicOn;
	}

	public boolean getSound(){
		return soundOn;
	}

	public void loadAssets(String assets){
		if(assets.equals("Menu")){
			if(!assetManager.isLoaded("background.jpg", Texture.class)){
				assetManager.load("delius.fnt", BitmapFont.class);
				assetManager.load("sounds/happyCave.wav", Music.class);
				assetManager.load("sounds/spaceShip.wav", Sound.class);
				assetManager.load("star1.png", Texture.class);
				assetManager.load("star2.png", Texture.class);
				assetManager.load("star3.png", Texture.class);
				assetManager.load("star4.png", Texture.class);
				assetManager.load("meteorit.png", Texture.class);
				assetManager.load("comet.png", Texture.class);
				assetManager.load("satelite.png", Texture.class);
				assetManager.load("alien.png", Texture.class);
				assetManager.load("background.jpg", Texture.class);
			}
			assetManager.load("cebiac.png", Texture.class);
			assetManager.load("aceptButt.png", Texture.class);
			assetManager.load("bangLogo.png", Texture.class);
			assetManager.load("newBangButt.png", Texture.class);
			assetManager.load("oldBangButt.png", Texture.class);
			assetManager.load("characteres.png", Texture.class);
			assetManager.load("siButt.png", Texture.class);
			assetManager.load("noButt.png", Texture.class);
			assetManager.load("userName.png", Texture.class);
			assetManager.load("signIn.png", Texture.class);
			assetManager.load("signUp.png", Texture.class);
		}else if(assets.equals("Mission")){
			assetManager.load("captain.png", Texture.class);
			assetManager.load("crispi.png", Texture.class);
			assetManager.load("cesia.png", Texture.class);
			assetManager.load("cori.png", Texture.class);
			assetManager.load("carmel.png", Texture.class);
			assetManager.load("cristal.png", Texture.class);
			assetManager.load("dialogCaptain.png", Texture.class);
			assetManager.load("dialogCesia.png", Texture.class);
			assetManager.load("dialogCori.png", Texture.class);
			assetManager.load("dialogCarmel.png", Texture.class);
			assetManager.load("dialogCristal.png", Texture.class);
			assetManager.load("alienChar.png", Texture.class);
			assetManager.load("nextButt.png", Texture.class);
			assetManager.load("question.png", Texture.class);
			assetManager.load("ideaBox.png", Texture.class);
			assetManager.load("addButt.png", Texture.class);
			assetManager.load("aceptButt.png", Texture.class);
			assetManager.load("hand.png", Texture.class);
			assetManager.load("rocket.png", Texture.class);
			assetManager.load("fire.png", Texture.class);
			assetManager.load("galaxy.png", Texture.class);
			assetManager.load("planet1.png", Texture.class);
			assetManager.load("planet2.png", Texture.class);
			assetManager.load("planet3.png", Texture.class);
			assetManager.load("planet4.png", Texture.class);
			assetManager.load("planet5.png", Texture.class);
			assetManager.load("sun.png", Texture.class);
			assetManager.load("randButt.png", Texture.class);
			assetManager.load("n1.png", Texture.class);
			assetManager.load("n2.png", Texture.class);
			assetManager.load("n3.png", Texture.class);
			assetManager.load("n4.png", Texture.class);
			assetManager.load("n5.png", Texture.class);
			assetManager.load("n6.png", Texture.class);
			assetManager.load("n7.png", Texture.class);
			assetManager.load("n8.png", Texture.class);
			assetManager.load("n9.png", Texture.class);
			assetManager.load("n10.png", Texture.class);
			assetManager.load("n11.png", Texture.class);
			assetManager.load("n12.png", Texture.class);
			assetManager.load("n13.png", Texture.class);
			assetManager.load("n14.png", Texture.class);
			assetManager.load("n15.png", Texture.class);
			assetManager.load("cards/maze1.png", Texture.class);
			assetManager.load("cards/maze2.png", Texture.class);
			assetManager.load("cards/maze3.png", Texture.class);
			assetManager.load("cards/maze4.png", Texture.class);
			assetManager.load("cards/maze5.png", Texture.class);
			assetManager.load("cards/rand1.png", Texture.class);
			assetManager.load("cards/rand2.png", Texture.class);
			assetManager.load("cards/rand3.png", Texture.class);
			assetManager.load("cards/rand4.png", Texture.class);
			assetManager.load("cards/rand5.png", Texture.class);
			assetManager.load("cards/head1.png", Texture.class);
			assetManager.load("cards/head2.png", Texture.class);
			assetManager.load("cards/head3.png", Texture.class);
			assetManager.load("cards/head4.png", Texture.class);
			assetManager.load("cards/head5.png", Texture.class);
			assetManager.load("ideasTitle.png", Texture.class);
			assetManager.load("lapiz.png", Texture.class);
			assetManager.load("addIdeaButt.png", Texture.class);
			assetManager.load("winBack.png", Texture.class);
			assetManager.load("firework1.png", Texture.class);
			assetManager.load("firework2.png", Texture.class);
			assetManager.load("gift.png", Texture.class);
			assetManager.load("comodin1G.png", Texture.class);
			assetManager.load("comodin2G.png", Texture.class);
			assetManager.load("comodin1.png", Texture.class);
			assetManager.load("comodin2.png", Texture.class);
			assetManager.load("needs/iconN1.png", Texture.class);
			assetManager.load("needs/iconN2.png", Texture.class);
			assetManager.load("needs/iconN3.png", Texture.class);
			assetManager.load("needs/iconN4.png", Texture.class);
			assetManager.load("needs/iconN5.png", Texture.class);
			assetManager.load("needs/iconN6.png", Texture.class);
			assetManager.load("needs/iconN7.png", Texture.class);
			assetManager.load("needs/iconN8.png", Texture.class);
			assetManager.load("needs/iconN9.png", Texture.class);
			assetManager.load("needs/iconN10.png", Texture.class);
			assetManager.load("needs/iconN11.png", Texture.class);
			assetManager.load("needs/iconN12.png", Texture.class);
			assetManager.load("needs/iconN13.png", Texture.class);
			assetManager.load("needs/iconN14.png", Texture.class);
			assetManager.load("needs/iconN15.png", Texture.class);
		}else if(assets.equals("Storyboard")){
			Gdx.app.log("EntreS", "SIP");
			if(!assetManager.isLoaded("firework2.png", Texture.class)){
				assetManager.load("firework1.png", Texture.class);
				assetManager.load("firework2.png", Texture.class);
			}
            if(!assetManager.isLoaded("storyboard/butts/remove.png", Texture.class)){
				assetManager.load("winBack2.png", Texture.class);
				assetManager.load("trophie.png", Texture.class);
				assetManager.load("mainMenuButt.png", Texture.class);
                assetManager.load("storyboard/arrow.png", Texture.class);
                assetManager.load("storyboard/boxSmall.png", Texture.class);
                assetManager.load("storyboard/boxBig.png", Texture.class);
                assetManager.load("storyboard/toolsButt.png", Texture.class);
                assetManager.load("storyboard/editToolsButt.png", Texture.class);
                assetManager.load("storyboard/editToolsBox.png", Texture.class);
                assetManager.load("storyboard/tools/default/default1.png", Texture.class);
                assetManager.load("storyboard/tools/default/default2.png", Texture.class);
                assetManager.load("storyboard/tools/default/default3.png", Texture.class);
                assetManager.load("storyboard/tools/default/default4.png", Texture.class);
                assetManager.load("storyboard/tools/default/default5.png", Texture.class);
                assetManager.load("storyboard/tools/default/default6.png", Texture.class);
                assetManager.load("storyboard/tools/default/default7.png", Texture.class);
                assetManager.load("storyboard/tools/default/default8.png", Texture.class);
                assetManager.load("storyboard/tools/default/default9.png", Texture.class);
                assetManager.load("storyboard/tools/default/default10.png", Texture.class);
                assetManager.load("storyboard/tools/default/default11.png", Texture.class);
                assetManager.load("storyboard/tools/default/default12.png", Texture.class);
                assetManager.load("storyboard/tools/default/default13.png", Texture.class);
                assetManager.load("storyboard/tools/default/default14.png", Texture.class);
                assetManager.load("storyboard/tools/default/default15.png", Texture.class);
                assetManager.load("storyboard/tools/default/default16.png", Texture.class);
                assetManager.load("storyboard/tools/default/default17.png", Texture.class);
                assetManager.load("storyboard/tools/default/default18.png", Texture.class);
                assetManager.load("storyboard/tools/default/default19.png", Texture.class);
                assetManager.load("storyboard/butts/save.png", Texture.class);
                assetManager.load("storyboard/butts/zoomIn.png", Texture.class);
                assetManager.load("storyboard/butts/zoomOut.png", Texture.class);
                assetManager.load("storyboard/butts/text.png", Texture.class);
                assetManager.load("storyboard/butts/moreZ.png", Texture.class);
                assetManager.load("storyboard/butts/lessZ.png", Texture.class);
                assetManager.load("storyboard/butts/remove.png", Texture.class);
            }
		}else if(assets.equals("getBangs")){
			assetManager.load("hand.png", Texture.class);
			assetManager.load("aceptButt.png", Texture.class);
			assetManager.load("ideasTitle.png", Texture.class);
			if(!assetManager.isLoaded("retomar/filterOut.png", Texture.class)){
                assetManager.load("retomar/filterBox.png", Texture.class);
                assetManager.load("retomar/ideaBox.png", Texture.class);
                assetManager.load("retomar/ideaBox0.png", Texture.class);
                assetManager.load("retomar/ideaBox1.png", Texture.class);
                assetManager.load("retomar/ideaBox2.png", Texture.class);
                assetManager.load("retomar/ideaBox3.png", Texture.class);
                assetManager.load("retomar/ideaBox4.png", Texture.class);
                assetManager.load("retomar/arrow.png", Texture.class);
                assetManager.load("retomar/filterOut.png", Texture.class);
            }
            if(!assetManager.isLoaded("needs/iconN15.png", Texture.class)){
                assetManager.load("needs/iconN1.png", Texture.class);
                assetManager.load("needs/iconN2.png", Texture.class);
                assetManager.load("needs/iconN3.png", Texture.class);
                assetManager.load("needs/iconN4.png", Texture.class);
                assetManager.load("needs/iconN5.png", Texture.class);
                assetManager.load("needs/iconN6.png", Texture.class);
                assetManager.load("needs/iconN7.png", Texture.class);
                assetManager.load("needs/iconN8.png", Texture.class);
                assetManager.load("needs/iconN9.png", Texture.class);
                assetManager.load("needs/iconN10.png", Texture.class);
                assetManager.load("needs/iconN11.png", Texture.class);
                assetManager.load("needs/iconN12.png", Texture.class);
                assetManager.load("needs/iconN13.png", Texture.class);
                assetManager.load("needs/iconN14.png", Texture.class);
                assetManager.load("needs/iconN15.png", Texture.class);
            }
		}else if(assets.equals("Splash")){
			for(int i = 1; i <= 58; i++){
				assetManager.load("splash/cometSheet"+i+".png", Texture.class);
			}
		}
	}

    public void setProgress(){
        float currentProg = assetManager.getProgress();
        float newSize = (currentProg*577.35f);
        barLoad.setWidth(newSize);
        endBarLoad.setX(barLoad.getX()+barLoad.getWidth());
    }
}
