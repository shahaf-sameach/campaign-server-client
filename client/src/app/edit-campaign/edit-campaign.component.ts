import { Inject, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {FormGroup, FormBuilder, Validators, FormControl} from '@angular/forms';
import { CampaignService } from '../campaign.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import {Campaign, Data, Cap} from '../models';



@Component({
  selector: 'app-edit-campaign',
  templateUrl: './edit-campaign.component.html',
  styleUrls: ['./edit-campaign.component.css']
})
export class EditCampaignComponent implements OnInit {

  angForm: FormGroup;
  campaign: any = {};

  constructor(private bs: CampaignService,
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<EditCampaignComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  createForm(campaign) {
    this.angForm = this.fb.group({
      campaign_name: new FormControl(campaign.name, [Validators.required]),
      data_message: new FormControl(campaign.data.message, [Validators.required]),
      max_count_per_user: new FormControl(campaign.cap.max_count_per_user, [Validators.required]),
      max_count: new FormControl(campaign.cap.max_count, [Validators.required])
      });
    }


  ngOnInit() {
    this.campaign = this.getEmptyCampaign();
    if (this.data.action === 'update') {
      this.campaign = this.data.campaign;
    }

    this.createForm(this.campaign);
  }

  getEmptyCampaign() {
    const campaign = {} as Campaign;
    campaign.id = this.campaign.id;
    campaign.name = null;
    campaign.data = {} as Data;
    campaign.data.message = null;

    campaign.cap = {} as Cap;
    campaign.cap.max_count_per_user = null;
    campaign.cap.max_count = null;

    return campaign;
  }


  updateBusiness() {
    const campaign = {} as Campaign;
    campaign.id = this.campaign.id;

    campaign.name = this.angForm.value.campaign_name;

    campaign.data = {} as Data;
    campaign.data.message = this.angForm.value.data_message;

    campaign.cap = {} as Cap;
    campaign.cap.max_count_per_user = this.angForm.value.max_count_per_user;
    campaign.cap.max_count = this.angForm.value.max_count;

    this.bs.updateBusiness(campaign).subscribe(i => console.log(i));
    this.dialogRef.close({ success: true });
  }
}
