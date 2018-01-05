/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { CompanyEarningDetailComponent } from '../../../../../../main/webapp/app/entities/company-earning/company-earning-detail.component';
import { CompanyEarningService } from '../../../../../../main/webapp/app/entities/company-earning/company-earning.service';
import { CompanyEarning } from '../../../../../../main/webapp/app/entities/company-earning/company-earning.model';

describe('Component Tests', () => {

    describe('CompanyEarning Management Detail Component', () => {
        let comp: CompanyEarningDetailComponent;
        let fixture: ComponentFixture<CompanyEarningDetailComponent>;
        let service: CompanyEarningService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyEarningDetailComponent],
                providers: [
                    CompanyEarningService
                ]
            })
            .overrideTemplate(CompanyEarningDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyEarningDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyEarningService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new CompanyEarning(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.companyEarningType).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
