/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { GenderDetailComponent } from '../../../../../../main/webapp/app/entities/gender/gender-detail.component';
import { GenderService } from '../../../../../../main/webapp/app/entities/gender/gender.service';
import { Gender } from '../../../../../../main/webapp/app/entities/gender/gender.model';

describe('Component Tests', () => {

    describe('Gender Management Detail Component', () => {
        let comp: GenderDetailComponent;
        let fixture: ComponentFixture<GenderDetailComponent>;
        let service: GenderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [GenderDetailComponent],
                providers: [
                    GenderService
                ]
            })
            .overrideTemplate(GenderDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GenderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GenderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Gender(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.gender).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
