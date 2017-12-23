/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { CompanyLocationDetailComponent } from '../../../../../../main/webapp/app/entities/company-location/company-location-detail.component';
import { CompanyLocationService } from '../../../../../../main/webapp/app/entities/company-location/company-location.service';
import { CompanyLocation } from '../../../../../../main/webapp/app/entities/company-location/company-location.model';

describe('Component Tests', () => {

    describe('CompanyLocation Management Detail Component', () => {
        let comp: CompanyLocationDetailComponent;
        let fixture: ComponentFixture<CompanyLocationDetailComponent>;
        let service: CompanyLocationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyLocationDetailComponent],
                providers: [
                    CompanyLocationService
                ]
            })
            .overrideTemplate(CompanyLocationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyLocationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyLocationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new CompanyLocation(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.companyLocation).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
