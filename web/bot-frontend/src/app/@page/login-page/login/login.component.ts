import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../../@service/security/auth.service';
import {ActivatedRoute, Router} from '@angular/router';
import {UserApiService} from '../../../@service/api/user-api.service';
import {Observable} from 'rxjs';
import {Constant} from '../../../@shared/app.constant';
import {User} from '../../../@model/user.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  isLogin$: Observable<boolean> = new Observable<boolean>();
  errorMessage = '';
  currentUser: any;
  discordURL = Constant.DISCORD_AUTH_URL;

  constructor(private authService: AuthService,
              private userApiService: UserApiService,
              private route: ActivatedRoute,
              private router: Router) {}

  ngOnInit(): void {
    this.isLogin$ = this.authService.loginState.asObservable();

    const token: string | null = this.route.snapshot.queryParamMap.get('token');
    const error: string | null = this.route.snapshot.queryParamMap.get('error');

    if (token) {
      this.authService.saveToken(token);
      this.userApiService.getCurrentUser().subscribe(
        data => {
          this.login(data);
        }, err => {
          this.errorMessage = err.error.message;
        }
      );
    } else if (error) {
      this.errorMessage = error;
    }
  }

  login(user: User): void {
    this.authService.saveUser(user);
    this.currentUser = this.authService.getUser();
    this.router.navigateByUrl('/home');
  }
}
