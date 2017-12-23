/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { CompanyContactDetailComponent } from '../../../../../../main/webapp/app/entities/company-contact/company-contact-detail.component';
import { CompanyContactService } from '../../../../../../main/webapp/app/entities/company-contact/company-contact.service';
import { CompanyContact } from '../../../../../../main/webapp/app/entities/company-contact/company-contact.model';

describe('Component Tests', () => {

    describe('CompanyContact Management Detail Component', () => {
        let comp: CompanyContactDetailComponent;
        let fixture: ComponentFixture<CompanyContactDetailComponent>;
        let service: CompanyContactService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyContactDetailComponent],
                providers: [
                    CompanyContactService
                ]
            })
            .overrideTemplate(CompanyContactDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyContactDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyContactService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new CompanyContact(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.companyContact).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
