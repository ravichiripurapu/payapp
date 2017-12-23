/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { CompanyBankDetailComponent } from '../../../../../../main/webapp/app/entities/company-bank/company-bank-detail.component';
import { CompanyBankService } from '../../../../../../main/webapp/app/entities/company-bank/company-bank.service';
import { CompanyBank } from '../../../../../../main/webapp/app/entities/company-bank/company-bank.model';

describe('Component Tests', () => {

    describe('CompanyBank Management Detail Component', () => {
        let comp: CompanyBankDetailComponent;
        let fixture: ComponentFixture<CompanyBankDetailComponent>;
        let service: CompanyBankService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyBankDetailComponent],
                providers: [
                    CompanyBankService
                ]
            })
            .overrideTemplate(CompanyBankDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyBankDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyBankService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new CompanyBank(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.companyBank).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
