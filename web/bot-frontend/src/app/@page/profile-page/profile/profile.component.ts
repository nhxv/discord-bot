import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../../@service/security/auth.service';
import {User} from '../../../@model/user.model';
import {Guild} from '../../../@model/guild.model';
import {Constant} from '../../../@shared/app.constant';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user?: User;
  guilds: Guild[] = [];

  constructor(private authService: AuthService,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.user = this.authService.getUser() as User;
    this.filterGuild();
  }

  filterGuild(): void {
    // @ts-ignore
    this.guilds = this.user.guilds
    .map((guild: Guild) => {
      if (guild.icon != null) {
        const newGuild = {...guild};
        newGuild.icon = Constant.DISCORD_BASE_IMG + 'icons/' + guild.id + '/' + guild.icon + '.png';
        return newGuild;
      } else {
        return guild;
      }
    });
  }

  onOpenGuild(id: string): void {
    this.router.navigate([`guild-bot-edit/${id}`], {relativeTo: this.route});
  }

}
