export class GuildBot {
  constructor(
    public id: string,
    public prefix: string,
    public name: string,
    public enableLog: boolean,
    public enableWelcome: boolean,
    public logChannel?: string,
    public welcomeChannel?: string,
    public welcomeText?: string,
  ) {}
}
