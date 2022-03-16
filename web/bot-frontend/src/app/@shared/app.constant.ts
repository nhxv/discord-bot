export class Constant {
  private static API_BASE_URL = 'http://localhost:8080/';
  private static OAUTH2_URL = Constant.API_BASE_URL + 'oauth2/authorization/';
  private static REDIRECT_URL = '?redirect_uri=http://localhost:4200/login';
  public static DISCORD_AUTH_URL = Constant.OAUTH2_URL + 'discord' + Constant.REDIRECT_URL;
  public static DISCORD_BASE_IMG = 'https://cdn.discordapp.com/';
}
