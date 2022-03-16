import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {GuildBot} from '../../@model/guild-bot.model';

@Injectable({providedIn: 'root'})
export class GuildBotApiService {
  constructor(private http: HttpClient) {}

  getGuildBot(guildBotId: string): Observable<any> {
    return this.http.get(`http://localhost:8080/api/guildBot/${guildBotId}`);
  }

  saveGuildBot(guildBot: GuildBot): Observable<any> {
    return this.http.post(`http://localhost:8080/api/guildBot/`, guildBot);
  }
}
