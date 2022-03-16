import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {GuildBotApiService} from '../../../@service/api/guild-bot-api.service';
import {GuildBot} from '../../../@model/guild-bot.model';
import {Guild} from '../../../@model/guild.model';
import {Channel} from '../../../@model/channel.model';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-guild-bot-edit',
  templateUrl: './guild-bot-edit.component.html',
  styleUrls: ['./guild-bot-edit.component.scss'],
  providers: [MessageService]
})
export class GuildBotEditComponent implements OnInit {
  guildBot: GuildBot | undefined;
  guildBotId = '';

  prefix = '';

  // edit attr
  enableLog = false;
  enableWelcome = false;

  // channels that bot can send message to
  channels: Channel[];
  selectedLogChannel: Channel = null;
  selectedWelcomeChannel: Channel = null;

  welcomeText = '';

  @ViewChild('f') form: any;
  noSpaceRegex = /^([A-z0-9!@#$%^&*().,<>{}[\]<>?_=+\-|;:\'\"\/])*[^\s]\1*$/;

  constructor(private route: ActivatedRoute,
              private guildBotApi: GuildBotApiService,
              private messageService: MessageService) {}

  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    this.guildBotId = routeParams.get('guildBotId');
    const guild: Guild = this.findGuild(this.guildBotId);
    if (guild) {
      this.channels = guild.channels;

    }
    this.guildBotApi.getGuildBot(this.guildBotId).subscribe((data) => {
      this.guildBot = data;
      if (this.guildBot) {
        this.prefix = this.guildBot.prefix;
        this.enableLog = this.guildBot.enableLog;
        this.enableWelcome = this.guildBot.enableWelcome;
        this.selectedLogChannel = this.findChannel(this.guildBot.logChannel);
        this.selectedWelcomeChannel = this.findChannel(this.guildBot.welcomeChannel);
        this.welcomeText = this.guildBot.welcomeText;
      }
    });
  }

  findGuild(guildBotId: string): Guild {
    const guilds: Guild[] = JSON.parse(sessionStorage.getItem('guilds'));
    for (const g of guilds) {
      if (g.id === guildBotId) { return g; }
    }
    return null;
  }

  findChannel(channelId: string): Channel {
    for (const channel of this.channels) {
      if (channel.id === channelId) { return channel; }
    }
    return null;
  }

  onSubmit(): void {
    if (this.form.valid) {
      const logChannelId = this.selectedLogChannel ? this.selectedLogChannel.id : '0';
      const welcomeChannelId = this.selectedWelcomeChannel ? this.selectedWelcomeChannel.id : '0';
      const guildBot: GuildBot = new GuildBot(
        this.guildBotId,
        this.form.value.prefix,
        'MichaelBot',
        this.enableLog,
        this.enableWelcome,
        logChannelId,
        welcomeChannelId,
        this.form.value.welcomeText,
      );
      this.guildBotApi.saveGuildBot(guildBot).subscribe(() => {
        this.messageService.add({key: 'bc', severity: 'success', summary: 'Saved', detail: 'content saved successfully'});
      }, error => {
        this.messageService.add({key: 'bc', severity: 'error', summary: 'Save Failed', detail: error.message});
      });
    } else {
      this.messageService.add({key: 'bc', severity: 'error', summary: 'Save Failed', detail: 'Invalid form'});
    }
  }

  selectWelcomeChannel(e): void {
    if (e && !this.selectedWelcomeChannel) {
      this.selectedWelcomeChannel = this.channels[0];
    }
  }

}
