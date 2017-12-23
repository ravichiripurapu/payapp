/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { CompanyCompensationDetailComponent } from '../../../../../../main/webapp/app/entities/company-compensation/company-compensation-detail.component';
import { CompanyCompensationService } from '../../../../../../main/webapp/app/entities/company-compensation/company-compensation.service';
import { CompanyCompensation } from '../../../../../../main/webapp/app/entities/company-compensation/company-compensation.model';

describe('Component Tests', () => {

    describe('CompanyCompensation Management Detail Component', () => {
        let comp: CompanyCompensationDetailComponent;
        let fixture: ComponentFixture<CompanyCompensationDetailComponent>;
        let service: CompanyCompensationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyCompensationDetailComponent],
                providers: [
                    CompanyCompensationService
                ]
            })
            .overrideTemplate(CompanyCompensationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyCompensationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyCompensationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new CompanyCompensation(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.companyCompensation).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
