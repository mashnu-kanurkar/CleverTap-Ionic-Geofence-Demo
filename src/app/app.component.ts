import { Component } from '@angular/core';
import {CleverTap} from '@awesome-cordova-plugins/clevertap'
import { Platform, ToastController } from '@ionic/angular';


@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent {

  constructor(public toastController: ToastController) {
    CleverTap.setDebugLevel(3);
    //Create notification channel for Android O and above
    CleverTap.createNotificationChannel("BRTesting", "BRTesting", "BRTesting", 5, true);
  
  }
}
