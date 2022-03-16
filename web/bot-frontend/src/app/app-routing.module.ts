import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {HomePageComponent} from './@page/home-page/home-page.component';
import {LoginPageComponent} from './@page/login-page/login-page.component';
import {ProfilePageComponent} from './@page/profile-page/profile-page.component';
import {GuildBotEditPageComponent} from './@page/guild-bot-edit-page/guild-bot-edit-page.component';
import {NotFoundPageComponent} from './@page/not-found-page/not-found-page.component';
import {UserGuard} from './@service/security/user.guard';

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: HomePageComponent},
  {path: 'login', component: LoginPageComponent},
  {path: 'profile', component: ProfilePageComponent, canActivate: [UserGuard]},
  {path: 'profile/guild-bot-edit/:guildBotId', component: GuildBotEditPageComponent, canActivate: [UserGuard]},
  {path: 'not-found', component: NotFoundPageComponent},
  {path: '**', redirectTo: '/not-found'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
