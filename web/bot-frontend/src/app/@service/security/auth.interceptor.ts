import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Router} from '@angular/router';
import {AuthService} from './auth.service';
import {tap} from 'rxjs/operators';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService, private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let authReq = req;
    const loginPath = '/login';
    const token = this.authService.getToken();
    if (token) {
      authReq = req.clone({
        setHeaders: {
          Authorization: 'Bearer ' + token
        }
      });
    }
    return next.handle(authReq).pipe( tap(() => {},
    (err: any) => {
      if (err instanceof HttpErrorResponse) {
        if (err.status !== 401 || window.location.pathname === loginPath) {
          return;
        }
        this.authService.signOut();
        window.location.href = loginPath;
      }
    }
    ));
  }
}
