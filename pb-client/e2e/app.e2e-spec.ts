import { PbClientPage } from './app.po';

describe('pb-client App', () => {
  let page: PbClientPage;

  beforeEach(() => {
    page = new PbClientPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
