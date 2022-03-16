import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {User} from '../../@model/user.model';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';
const GUILD_KEY = 'guilds';

@Injectable({providedIn: 'root'})
export class AuthService {
  loginState: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(!!sessionStorage.getItem(TOKEN_KEY));

  signOut(): void {
    sessionStorage.clear();
    this.loginState.next(!!sessionStorage.getItem(TOKEN_KEY));
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
    this.loginState.next(!!sessionStorage.getItem(TOKEN_KEY));
  }

  public saveUser(user: User): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));

    window.sessionStorage.removeItem(GUILD_KEY);
    window.sessionStorage.setItem(GUILD_KEY, JSON.stringify(user.guilds));
    this.loginState.next(!!sessionStorage.getItem(TOKEN_KEY));
  }

  public getToken(): string {
    return (sessionStorage.getItem(TOKEN_KEY) as string);
  }

  public getUser(): any {
    return JSON.parse(sessionStorage.getItem(USER_KEY) as string);
  }
}
