import {Channel} from './channel.model';

export class Guild {
  constructor(
    public id: string,
    public name: string,
    public icon: string,
    public permissions: string,
    public owner: boolean,
    public channels: Channel[]
  ) {}
}
