/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { CompanyBankComponent } from '../../../../../../main/webapp/app/entities/company-bank/company-bank.component';
import { CompanyBankService } from '../../../../../../main/webapp/app/entities/company-bank/company-bank.service';
import { CompanyBank } from '../../../../../../main/webapp/app/entities/company-bank/company-bank.model';

describe('Component Tests', () => {

    describe('CompanyBank Management Component', () => {
        let comp: CompanyBankComponent;
        let fixture: ComponentFixture<CompanyBankComponent>;
        let service: CompanyBankService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyBankComponent],
                providers: [
                    CompanyBankService
                ]
            })
            .overrideTemplate(CompanyBankComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyBankComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyBankService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new CompanyBank(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.companyBanks[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
