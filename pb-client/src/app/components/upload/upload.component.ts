import { BankFormat } from './../../dto/bank-format';
import { BankHttpService } from './../../service/bank-http.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AccountHttpService } from '../../service/account-http.service';
import { Subscription } from 'rxjs/Subscription';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Account } from '../../dto/account';
import { UploadResponse } from '../../dto/upload-response';
import { TransactionService } from '../../service/transaction.service';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent implements OnInit {

  public account: Account;
  public uploadForm: FormGroup;
  public file: File;
  public bankFormats: BankFormat[];
  public uploadResults: {
    fileName: string,
    uploadResponse: UploadResponse
  }[] = [];

  private subscriptions: Subscription[] = [];


  constructor(private fb: FormBuilder,
              private route: ActivatedRoute, 
              private accountService: AccountHttpService,
              private transactionService: TransactionService,
              private bankService: BankHttpService) {
  }

  ngOnInit(): void {
    this.uploadForm = this.fb.group({
      file: new FormControl(''),
      format: new FormControl('', Validators.required)
    });
    this.subscriptions.push(this.route.params.map(params => +params['id']).subscribe(
      id => {
        this.subscriptions.push(this.accountService.getAccount(id).subscribe(account => {
          this.account = account
          this.subscriptions.push(this.bankService.getBankFormats(account.bank).subscribe(formats => {
            this.bankFormats = formats;
            this.uploadForm.controls['format'].setValue(formats[0].formatName);
          }));
        }));
      }
    ));
  }

  public fileChange(event): void {
    let fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      this.file = fileList[0];
    }
  }

  public upload(): void {
    if (this.file && this.uploadForm.valid) {
      this.subscriptions.push(this.transactionService.uploadTransactions(this.account, this.file, this.uploadForm.controls['format'].value).subscribe(
        response => {
          this.uploadResults.push({
            fileName: this.file.name,
            uploadResponse: response
          });
        }
      ));
    }
  }


}
