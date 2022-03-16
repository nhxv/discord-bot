export class Channel {
  constructor(
    public id: string,
    public name: string,
    public permissions: string,
    public type: number,
  ) {}
}
