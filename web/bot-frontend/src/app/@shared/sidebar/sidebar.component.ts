import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../../@service/security/auth.service';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {
  isLoggedIn$: Observable<boolean> = new Observable<boolean>();

  constructor(private router: Router,
              private authService: AuthService) { }

  ngOnInit(): void {
    this.isLoggedIn$ = this.authService.loginState.asObservable();
  }

  getUsername(): string {
    const user = this.authService.getUser();
    return user ? user.displayName : '';
  }

  onSignOut(): void {
    this.authService.signOut();
    this.router.navigateByUrl('/login');
  }

}
