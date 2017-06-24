
package com.RNRSA;

import com.facebook.react.bridge.NoSuchKeyException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.Promise;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class RNRSAModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNRSAModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNRSA";
  }

  @ReactMethod
  public void generate(Promise promise)  {
    WritableNativeMap keys = new WritableNativeMap();

    try {
        RSA rsa = new RSA();
        rsa.generate();
        keys.putString("public",  rsa.getPublicKey());
        keys.putString("private",  rsa.getPrivateKey());
        promise.resolve(keys);
    } catch(NoSuchAlgorithmException e) {
      promise.reject("Error", e.getMessage());
    } catch(IOException e) {
      promise.reject("Error", e.getMessage());
    }
  }

  @ReactMethod
  public void encrypt(String message, String publicKeyString, Promise promise)  {

      try {
          RSA rsa = new RSA();
          rsa.setPublicKey(publicKeyString);
          String encodedMessage = rsa.encrypt(message);
          promise.resolve(encodedMessage);
      } catch(Exception e) {
          promise.reject("Error", e.getMessage());
      }
  }


  @ReactMethod
  public void decrypt(String encodedMessage, String privateKeyString, Promise promise)  {

      try {
          RSA rsa = new RSA();
          rsa.setPrivateKey(privateKeyString);
          String message = rsa.decrypt(encodedMessage);
          promise.resolve(message);

      } catch(Exception e) {
          promise.reject("Error", e.getMessage());
      }
  }




//    @ReactMethod
//    public void decrypt(String encodedMessage, String privateKeyString)  {
//        WritableNativeMap keys = new WritableNativeMap();
//
//        try {
//
//            EncryptionManager.getInstance().generateKey();
//            keys.putString("public",  EncryptionManager.getInstance().getPublicKey());
//            keys.putString("private",  EncryptionManager.getInstance().getPrivateKey());
//
//        } catch(Exception e) {
//
//        }
//        callback.invoke(keys);
//    }
}