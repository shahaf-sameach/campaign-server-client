import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SlimLoadingBarModule } from 'ng2-slim-loading-bar';

import { AppComponent } from './app.component';
import { EditCampaignComponent } from './edit-campaign/edit-campaign.component';
import { MatDialogModule } from '@angular/material/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { CampaignService } from './campaign.service';

@NgModule({
  declarations: [
    AppComponent,
    EditCampaignComponent
  ],
  imports: [
    BrowserModule,
    SlimLoadingBarModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatDialogModule,
    BrowserAnimationsModule
  ],
  providers: [ CampaignService ],
  bootstrap: [ AppComponent ],
  entryComponents: [ EditCampaignComponent ]
})
export class AppModule { }
